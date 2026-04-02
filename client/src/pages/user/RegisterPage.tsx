import { useState } from "react"
import type { RegisterRequest } from "../../types/User";

export const RegisterPage = () => {
    const [form, setForm] = useState<RegisterRequest>({
        name: "",
        email: "",
        password: ""
    });


    return (
        <div>
            <h2>회원가입s</h2>
            <form>
                <div>
                    <label>name</label>
                    <input type="text"
                        onChange={(e) => setForm((prev) => ({ ...prev, name: e.target.value }))} />
                </div>
                <div>
                    <label>email</label>
                    <input type="text"
                        onChange={(e) => setForm((prev) => ({ ...prev, email: e.target.value }))} />
                </div>
                <div>
                    <label>passowrd</label>
                    <input type="text"
                        onChange={(e) => setForm((prev) => ({ ...prev, password: e.target.value }))} />
                </div>
            </form>
        </div>
    )
}