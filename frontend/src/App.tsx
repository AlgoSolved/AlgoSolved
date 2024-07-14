import React from "react";

import "./App.css";
import Footer from "./components/common/Footer";
import Seo from "./components/common/Seo";
import Router from "./routers/router";

function App() {
  return (
  <>
    <div className="App">
      <Seo />
      <Router />
    </div>
    <div className="footer">
      <Footer />
    </div>
  </>
  );
}

export default App;
