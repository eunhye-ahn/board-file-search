import type { LoginRequest } from "../../types/Auth"
import api from "../axiosInstance"

export const login = (data: LoginRequest) => {
    return api.post("/api/login", data)
}

export const logout = () => {
    return api.post("/api/logout")
}