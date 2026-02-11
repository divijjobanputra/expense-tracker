const BASE_URL = "http://localhost:8080/api";

const txnForm = document.getElementById("txnForm");
const errorBox = document.getElementById("errorBox");
const txnTableBody = document.getElementById("txnTableBody");

const monthInput = document.getElementById("month");
const summaryBox = document.getElementById("summaryBox");

function setError(msg) {
  errorBox.textContent = msg || "";
}

function getCurrentYearMonth() {
  const d = new Date();
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  return `${yyyy}-${mm}`;
}

async function loadTransactions() {
  setError("");
  txnTableBody.innerHTML = "";

  const res = await fetch(`${BASE_URL}/transactions`);
  const data = await res.json();

  for (const t of data) {
    const tr = document.createElement("tr");

    tr.innerHTML = `
      <td>${t.id}</td>
      <td>${t.type}</td>
      <td>${t.category}</td>
      <td>${t.amount}</td>
      <td>${t.txnDate}</td>
      <td>${t.note || ""}</td>
      <td><button data-id="${t.id}">Delete</button></td>
    `;

    tr.querySelector("button").addEventListener("click", async () => {
      await deleteTransaction(t.id);
    });

    txnTableBody.appendChild(tr);
  }
}

async function deleteTransaction(id) {
  setError("");
  const res = await fetch(`${BASE_URL}/transactions/${id}`, { method: "DELETE" });

  if (!res.ok) {
    const err = await res.json().catch(() => null);
    setError(err?.message || "Delete failed");
    return;
  }

  await loadTransactions();
  await loadSummary();
}

async function loadSummary() {
  setError("");
  const month = monthInput.value.trim();

  if (!month) {
    setError("Enter month like 2026-02");
    return;
  }

  const res = await fetch(`${BASE_URL}/summary?month=${encodeURIComponent(month)}`);
  const data = await res.json();

  if (!res.ok) {
    setError(data?.message || "Summary failed");
    return;
  }

  summaryBox.innerHTML = `
    <p>Total Income: ${data.totalIncome}</p>
    <p>Total Expense: ${data.totalExpense}</p>
    <p>Balance: ${data.balance}</p>
  `;
}

txnForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  setError("");

  const payload = {
    type: document.getElementById("type").value,
    category: document.getElementById("category").value,
    amount: Number(document.getElementById("amount").value),
    txnDate: document.getElementById("txnDate").value,
    note: document.getElementById("note").value
  };

  const res = await fetch(`${BASE_URL}/transactions`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });

  const data = await res.json().catch(() => null);

  if (!res.ok) {
    if (data?.details?.length) {
      setError(data.details.join(" | "));
    } else {
      setError(data?.message || "Create failed");
    }
    return;
  }

  txnForm.reset();
  document.getElementById("txnDate").value = new Date().toISOString().slice(0, 10);

  await loadTransactions();
  await loadSummary();
});

document.getElementById("refreshBtn").addEventListener("click", loadTransactions);
document.getElementById("loadSummaryBtn").addEventListener("click", loadSummary);

(function init() {
  document.getElementById("txnDate").value = new Date().toISOString().slice(0, 10);
  document.getElementById("txnDate").max = new Date().toISOString().split("T")[0];
  monthInput.value = getCurrentYearMonth();
  monthInput.max = getCurrentYearMonth();
  loadTransactions();
  loadSummary();
})();
