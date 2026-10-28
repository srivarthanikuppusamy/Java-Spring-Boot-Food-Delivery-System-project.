// Local dev fallback. Change to your live Render backend URL once deployed.
const API_BASE_URL = window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1" || window.location.protocol === "file:"
  ? "http://localhost:5000"
  : "https://YOUR_BACKEND_APP.onrender.com";

let currentUnitPrice = 220;

window.addEventListener('DOMContentLoaded', fetchSystemState);

async function fetchSystemState() {
  try {
    const res = await fetch(`${API_BASE_URL}/api/system/state`);
    const data = await res.json();

    document.getElementById('walletBalance').innerText = `₹${data.walletBalance.toFixed(2)}`;

    for (const [item, stock] of Object.entries(data.menuStock)) {
      const badge = document.getElementById(`stock-${item}`);
      if (badge) {
        if (stock <= 0) {
          badge.className = 'stock-badge out-of-stock';
          badge.innerText = 'Out of Stock (0)';
        } else {
          badge.className = 'stock-badge';
          badge.innerText = `Stock: ${stock} available`;
        }
      }
    }
  } catch (e) {
    console.error("Backend state fetch error:", e);
  }
}

function selectItem(name, price, element) {
  document.querySelectorAll('.menu-card').forEach(c => c.classList.remove('active'));
  element.classList.add('active');

  currentUnitPrice = price;
  document.getElementById('item').value = name;
  document.getElementById('unitPriceDisplay').value = `₹${price}`;

  recalculateTotal();
}

function adjustQuantity(delta) {
  const input = document.getElementById('quantity');
  let currentVal = parseInt(input.value) || 1;
  currentVal = Math.max(1, currentVal + delta);
  input.value = currentVal;
  recalculateTotal();
}

function recalculateTotal() {
  const qty = parseInt(document.getElementById('quantity').value) || 1;
  const coupon = document.getElementById('coupon').value.trim().toUpperCase();

  const subtotal = currentUnitPrice * qty;
  const discount = (coupon === "SAVE50") ? 50.0 : 0.0;
  const total = Math.max(0, subtotal - discount);

  document.getElementById('summarySubtotal').innerText = `₹${subtotal.toFixed(2)}`;
  document.getElementById('summaryDiscount').innerText = `-₹${discount.toFixed(2)}`;
  document.getElementById('summaryTotal').innerText = `₹${total.toFixed(2)}`;

  document.getElementById('payment').value = total;
}

async function placeOrder() {
  const statusBox = document.getElementById("statusBox");
  statusBox.style.display = "none";

  const payload = {
    item: document.getElementById("item").value,
    price: currentUnitPrice,
    quantity: parseInt(document.getElementById("quantity").value),
    address: document.getElementById("address").value,
    pincode: document.getElementById("pincode").value,
    coupon: document.getElementById("coupon").value,
    payment: parseFloat(document.getElementById("payment").value)
  };

  try {
    const response = await fetch(`${API_BASE_URL}/api/orders/checkout`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    const data = await response.json();

    if (response.ok) {
      statusBox.className = "alert-box alert-success";
      statusBox.innerText = data.message;
      fetchSystemState();
    } else {
      statusBox.className = "alert-box alert-danger";
      statusBox.innerHTML = `<strong>${data.exceptionType}:</strong> ${data.message}`;
    }
  } catch (err) {
    statusBox.className = "alert-box alert-danger";
    statusBox.innerText = `Unable to connect to server at ${API_BASE_URL}.`;
  }

  statusBox.style.display = "block";
}