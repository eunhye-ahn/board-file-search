//댓글조회(페이징)
export interface commentResponse {
    content: string,
    createdAt: string //?
}

export interface commentRequest {
    content: string
}