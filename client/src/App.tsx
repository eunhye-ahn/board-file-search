import { BrowserRouter, Route, Routes } from "react-router-dom"
import { HomePage } from "./pages/post/HomePage"
import { PostPage } from "./pages/post/PostPage"
import { UpdatePage } from "./pages/post/UpdatePage"
import { UploadPage } from "./pages/post/UploadPage"

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/posts/:postId" element={<PostPage />} />
        <Route path="/posts/:postId/update" element={<UpdatePage />} />
        <Route path="/posts" element={<UploadPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
