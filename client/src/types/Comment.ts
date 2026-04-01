//댓글조회(페이징)
export interface commentResponse {
    conmmentId: number,
    content: string,
    createdAt: string //?
}

export interface commentRequest {
    content: string
}