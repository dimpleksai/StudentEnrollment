// Uses CRA proxy in dev, else REACT_APP_API_BASE or fallback.
const API_BASE =
  process.env.NODE_ENV === "development" && !process.env.REACT_APP_API_BASE
    ? "" // dev: use proxy; fetch('/api/...') -> http://localhost:8081
    : (process.env.REACT_APP_API_BASE || "http://localhost:8081");

async function request(path, { method = "GET", body, headers } = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    method,
    headers: { "Content-Type": "application/json", ...(headers || {}) },
    body: body ? JSON.stringify(body) : undefined,
  });
  const data = await res.json().catch(() => ({}));
  if (!res.ok) {
    const msg = data?.message || `Request failed (${res.status})`;
    throw new Error(msg);
  }
  return data; // backend returns { message: "..." }
}

export const api = {
  signup: ({ name, email, password }) =>
    request("/api/auth/signup", { method: "POST", body: { name, email, password } }),
  login: ({ email, password }) =>
    request("/api/auth/login", { method: "POST", body: { email, password } }),
};
