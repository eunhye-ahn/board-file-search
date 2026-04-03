import { BrowserRouter, Route, Routes } from "react-router-dom"
import { HomePage } from "./pages/post/HomePage"
import { PostPage } from "./pages/post/PostPage"
import { UpdatePage } from "./pages/post/UpdatePage"
import { UploadPage } from "./pages/post/UploadPage"
import { LoginPage } from "./pages/auth/LoginPage"
import { RegisterPage } from "./pages/user/RegisterPage"

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/posts/:postId" element={<PostPage />} />
        <Route path="/posts/:postId/update" element={<UpdatePage />} />
        <Route path="/posts" element={<UploadPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
