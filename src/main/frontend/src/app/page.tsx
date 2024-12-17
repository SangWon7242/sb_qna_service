"use client";

import Link from "next/link";

export default function Home() {
  return (
    <div>
      <Link href="/">
        <button>홈 페이지 이동</button>
      </Link>
      &nbsp;
      <Link href="/question/list">
        <button>리스트 페이지로 이동</button>
      </Link>
    </div>
  );
}
