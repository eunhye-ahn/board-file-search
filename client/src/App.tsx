import { BrowserRouter, Route, Routes } from "react-router-dom"
import { HomePage } from "./pages/HomePage"
import { PostPage } from "./pages/PostPage"
import { UpdatePage } from "./pages/UpdatePage"

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/posts/:postId" element={<PostPage />} />
        <Route path="/posts/:postId/update" element={<UpdatePage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
