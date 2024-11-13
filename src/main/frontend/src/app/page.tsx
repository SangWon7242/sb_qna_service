"use client";

import React, { useState, useEffect } from "react";
import axios from "axios";

export default function App() {
  const [data, setData] = useState<string>("");
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const resp = await axios.get("/data");
        setData(resp.data);
      } catch (err) {
        console.error(`에러 발생 : ${err}`);
        setError(err as Error);
      }
    };

    fetchData();
  }, []);

  return <div>받아온 데이터 : {data}</div>;
}
