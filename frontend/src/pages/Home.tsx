import NavBar from '../components/common/Nav';
import Footer from '../components/common/Footer';
import Banner from '../components/common/Banner';
import CurrentList from '../components/home/CurrentList';



const Home = () => {
    return (
        <div>
            <NavBar />
            <Banner />
            <CurrentList />
            <Footer />
        </div>
    );
}

export default Home;
