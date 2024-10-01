import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route , Routes } from 'react-router-dom';
import Root from './pages/Root';
import Login from './pages/Login';
import Home from './pages/Home';
import Register from './pages/register/Register';
import Welcome from './pages/Welcome';
function App() {
  return (

      <Router>
        <Routes>
          <Route path='/' Component={Root} />
          <Route path='/login' Component={Login} />
          <Route path='/user/home' Component={Home} />
          <Route path='/user/register' Component={Register} />
          <Route path= '/user/welcome' Component={Welcome} />
        </Routes>
      </Router>

  );
}

export default App;
