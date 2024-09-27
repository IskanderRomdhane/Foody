import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route , Routes } from 'react-router-dom';
import Root from './pages/Root';
function App() {
  return (

      <Router>
        <Routes>
          <Route path='/' Component={Root} />
        </Routes>
      </Router>

  );
}

export default App;
