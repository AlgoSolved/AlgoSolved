import React from "react";
import { Helmet } from "react-helmet-async";

export const Seo = () => {
  return (
    <Helmet>
      <title>Algosolved</title>
      <meta
        name="description"
        content="알고리즘 문제 풀이 공유 사이트"
        data-react-helmet="true"
      />
      <meta property="og:title" content="algosolved" />
      <meta property="og:type" content="website" />
      <meta property="og:url" content="" />
      <meta property="og:image" content="" />
      <meta property="og:article:author" content="algosolved" />
    </Helmet>
  );
};

export default Seo;
