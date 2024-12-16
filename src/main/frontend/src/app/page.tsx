"use client";

import React, { useState, useEffect } from "react";
import axios from "axios";

export default function App() {
  const [data, setData] = useState<Array<String>>([]);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const resp = await axios.get("/api/home");
        setData(resp.data);
      } catch (err) {
        console.error(`에러 발생 : ${err}`);
        setError(err as Error);
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      <ul>
        {data.map((value, index) => (
          <li key={index}>
            {index + 1} : {value}
          </li>
        ))}
      </ul>
    </div>
  );
}
