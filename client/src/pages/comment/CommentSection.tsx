import { useEffect, useState } from "react";
import type { commentResponse } from "../../types/Comment";
import { getCommentsByPost } from "../../api/apis/comment";

export const CommentSection = ({ postId }: { postId: number }) => {
    const [page, setPage] = useState(1);
    const [loading, setLoading] = useState(true);
    const [comments, setComments] = useState<commentResponse[]>([]);

    //게시글이랑 댓글 분리
    useEffect(() => {
        console.log(postId)
        getCommentsByPost(postId, page)
            .then(res => {
                console.log(postId)
                console.log(res.data)
                setComments(res.data.content)
            })
            .finally(() => setLoading(false))
    }, [page, postId]);

    if (!postId) return <div>잘못된 접근입니다</div>;
    if (loading) return <div>로딩중...</div>
    if (!comments) return <div>데이터가 없습니다</div>;

    return (
        <div>
            {comments.map(comment => (
                <div key={comment.conmmentId}>
                    <p>{comment.content}</p>
                </div>
            ))}
            <button onClick={() => setPage(prev => prev - 1)}>이전</button>
            <button onClick={() => setPage(prev => prev + 1)}>다음</button>
        </div>
    )
}