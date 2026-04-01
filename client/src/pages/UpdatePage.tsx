import { useNavigate, useParams } from "react-router-dom"
import { getPost, updatePost } from "../api/apis/postApi";
import { useEffect, useState } from "react";
import type { PostDetailResponse, PostUpdateRequest } from "../types/Post";

/**
 * 
 * @returns export const updatePost = (postId: number, data: PostUpdateRequest, files?: File[]) => {
    const formData = new FormData();
    formData.append("request", new Blob([JSON.stringify(data)], { type: "application.json" }));
    files?.forEach(file => formData.append("files", file));
    return api.put(`/api/posts/${postId}`, formData);
}

export interface PostUpdateRequest {
    title: string,
    content: string,

}

 */

export const UpdatePage = () => {
    const [data, setData] = useState<PostDetailResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const { postId } = useParams<{ postId: string }>();
    const [form, setForm] = useState<PostUpdateRequest>({
        title: "",
        content: "",
        deleteFileIds: []
    });

    //보충필요 : 배열은 undefined 설정안하고 그냥 null?
    const [files, setFiles] = useState<File[]>([]);

    const postIdNum = Number(postId);

    useEffect(() => {
        if (!postId) return;
        getPost(postIdNum)
            .then(res => setData(res.data))
            .finally(() => setLoading(false));
    }, [postIdNum]);

    if (!postId) return <div>잘못된 접근입니다</div>;
    if (loading) return <div>로딩중...</div>
    if (!data) return <div>데이터가 없습니다</div>;


    const handleUpdate = () => {
        updatePost(postIdNum, form, files)
            .then(() => navigate(`/posts/${postIdNum}`))
    };

    return (
        <div>
            {/*상단헤더 작성자만 보이게?? */}
            <div>
                <h2>게시글 수정</h2>
            </div>
            {/**편집창*/}
            <div>
                <table>
                    <tbody>
                        <tr>
                            <th>제목</th>
                            <td>
                                <input type="text" defaultValue={data.title} />
                            </td>
                        </tr>
                        <tr>
                            <th>작성자</th>
                            <td>
                                ...
                            </td>
                        </tr>
                        <tr>
                            <th>등록일</th>
                            <td>{ }</td>
                        </tr>
                        <tr>
                            <th>내용</th>
                            <td>
                                <textarea />
                            </td>
                        </tr>
                        <tr>
                            <th>첨부파일</th>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            {/*하단 */}
            <div>
                <button onClick={() => navigate(-1)}>취소</button>
                <button onClick={handleUpdate}>수정</button>
            </div>
        </div>
    )
}