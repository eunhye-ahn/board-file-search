import axios from 'axios';

const api = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true
});

api.interceptors.response.use(
    res => res,
    err => {
        if (err.response?.status === 401) {
            window.location.href = "/login";
        }
        return Promise.reject(err); //401외 다른 에러는 호출부로 전달
    }
)

export default api;

/**
 * [WHAT] axios : HTTP 요청을 편하게 보내주는 라이브러리
 * [기능] axiosInstace : 기본 URL 설정, 쿠키 자동전송 설정 (요청마다 반복방지)
 *        interceptor(response/request) 
 *          : 요청 가로채기(모든 요청에 헤더추가 등)
 *          : 응답 가로채기(인증없는 로그인 이동처리 등)
 */