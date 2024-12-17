/** @type {import('next').NextConfig} */
const nextConfig = {
  async rewrites() {
    return [
      {
        source: "/api/:path*", // 기존 API 경로
        destination: "http://localhost:8080/api/:path*", // 백엔드 서버 경로
      },
    ];
  },
};

module.exports = nextConfig;
