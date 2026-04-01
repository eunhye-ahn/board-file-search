
import type { PostCreateRequest, PostUpdateRequest } from "../../types/Post";
import api from "../axiosInstance";

/**
 * 글작성
 * @param title
 * @param content
 * @param files
 */
export const createPost = (data: PostCreateRequest, files?: File[]) => {
    const formData = new FormData();
    formData.append("request", new Blob([JSON.stringify(data)], { type: "application.json" }));
    files?.forEach(file => formData.append("files", file));
    return api.post("/api/posts", formData);
}
/**
 * [WHAT] FormData : 파일 + 데이터 함께 서버로 전송
 *          -> 데이터(json)은 Blob으로 감싸야 같이 전송 가능
 *          -> new Blob(배열(데이터), 옵션(type 지정))
 *          -> append로 파일과 텍스트 따로 저장
 * 
 * [보충] MIME 타입 종류 
 *   application/json : json 데이터
 *   text/plain       : 일반 텍스트
 *   text/html        : HTML
 *   image/png        : png 이미지
 *   multipart/form-data : 폼 데이터(혼합데이터)
 */


export const deletePost = (postId: number) => {
    return api.delete(`/api/posts/${postId}`);
}

export const updatePost = (postId: number, data: PostUpdateRequest, files?: File[]) => {
    const formData = new FormData();
    formData.append("request", new Blob([JSON.stringify(data)], { type: "application.json" }));
    files?.forEach(file => formData.append("files", file));
    return api.put(`/api/posts/${postId}`, formData);
}

export const getPost = (postId: number) => {
    return api.get(`/api/posts/${postId}`);
}

export const getAllPosts = (page: number) => {
    return api.get("/api/posts", {
        params: { page }  //쿼리파라미터로 자동변환
    })
}