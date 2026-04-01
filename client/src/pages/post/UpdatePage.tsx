import { useNavigate, useParams } from "react-router-dom"
import { getPost, updatePost } from "../../api/apis/postApi";
import { useEffect, useState } from "react";
import type { PostDetailResponse, PostUpdateRequest } from "../../types/Post";

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

/**
 * 문제1 : defaultValue는 초기값만 설정되고 리액트가 이후 변경을 추적안함
 * 
 * 문제2 : File[] 에 object 넣으려고 하면 타입 에러 발생 > 근데 왜런타임중에?
 * 
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

    //상세데이터 중복 코드 => 리팩토링 고려
    useEffect(() => {
        if (!postId) return;
        getPost(postIdNum)
            .then(res => {
                setData(res.data)
                setForm({
                    title: res.data.title,
                    content: res.data.content,
                    deleteFileIds: []
                })
            })
            .finally(() => setLoading(false));
    }, [postIdNum]);

    if (!postId) return <div>잘못된 접근입니다</div>;
    if (loading) return <div>로딩중...</div>
    if (!data) return <div>데이터가 없습니다</div>;


    const handleUpdate = () => {
        updatePost(postIdNum, form, files)
            .then(() => navigate(`/posts/${postIdNum}`))
            .finally(() =>
                console.log(form))
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
                                <input type="text" value={form.title}
                                    onChange={(e) => setForm(prev => ({ ...prev, title: e.target.value }))} />
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
                            <td>{data.createdAt}</td>
                        </tr>
                        <tr>
                            <th>내용</th>
                            <td>
                                <textarea value={form.content}
                                    onChange={(e) => setForm(prev => ({ ...prev, content: e.target.value }))} />
                            </td>
                        </tr>
                        <tr>
                            <th>첨부파일</th>
                            <td>
                                {data.files?.map(file => (
                                    <div key={file.fileId}>
                                        <span>{file.fileName}</span>
                                        <button>삭제</button>
                                    </div>
                                ))}
                                {/* <input type="file" multiple onChange={(e) => setFiles(prev => ({ ...prev, file: e.target.value }))} /> */}
                                <input type="file" multiple
                                    onChange={(e) => {
                                        if (e.target.files) setFiles(Array.from(e.target.files))
                                    }} />
                            </td>
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