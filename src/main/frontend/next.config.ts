/** @type {import('next').NextConfig} */
const nextConfig = {
  async rewrites() {
    return [
      {
        source: "/", // 루트 경로 요청
        destination: "http://localhost:8080/", // 백엔드의 /api/로 전달
      },
      {
        source: "/api/:path*", // 기존 API 경로
        destination: "http://localhost:8080/api/:path*", // 백엔드 서버 경로
      },
    ];
  },
};

module.exports = nextConfig;
