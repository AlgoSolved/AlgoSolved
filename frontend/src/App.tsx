import React from "react";

import "./App.css";
import Seo from "./components/common/Seo";
import Router from "./routers/router";

function App() {
  return (
    <div className="App">
      <Seo />
      <Router />
    </div>
  );
}

export default App;
