import NavBar from "../components/common/Nav";
import Footer from "../components/common/Footer";
import Banner from "../components/common/Banner";
import SolutionList from "../components/home/SolutionList";

const Home = () => {
  return (
    <div>
      <div>
        <NavBar />
        <Banner />
        <SolutionList />
      </div>
      <div>
        <Footer />
      </div>
    </div>
  );
};

export default Home;
