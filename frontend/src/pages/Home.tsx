import NavBar from "../components/common/Nav";
import Footer from "../components/common/Footer";
import Banner from "../components/common/Banner";
import CurrentList from "../components/home/CurrentList";

const Home = () => {
  return (
    <div>
      <div>
        <NavBar />
        <Banner />
        <CurrentList />
      </div>
      <div>
        <Footer />
      </div>
    </div>
  );
};

export default Home;
