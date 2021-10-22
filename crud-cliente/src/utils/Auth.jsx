export function isLoggedIn() {
	return !!localStorage.getItem('roles')
}

export function getRolesFromCache () {
    return JSON.parse(localStorage.getItem("roles"))
}

export function getTokenFromCache () {
    return localStorage.getItem("token")
}

export function clearSessao() {
    localStorage.removeItem("roles")
    localStorage.removeItem("token")
}