import React, { useState } from 'react';
import loginUser from '../services/auth/authService';
import { useNavigate, Link } from 'react-router-dom';
import bg from '../assets/register/bg.webp'; // Correct import

const Login = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState(''); // Initialize state with empty strings
    const [password, setPassword] = useState('');

    const Cpass = (event) => {
        setPassword(event.target.value);
    }

    const Cemail = (event) => {
        setEmail(event.target.value);
    }

    const handleLogin = async () => {
        try {
            const result = await loginUser(email, password);
            console.log(result);
            navigate('/user/home');
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <div
            className="font-[sans-serif] min-h-screen flex items-center justify-center bg-cover"
            style={{ backgroundImage: `url(${bg})` }}
        >
            <div className="max-w-6xl w-full flex justify-center p-4 lg:p-6">
                <form className="bg-white rounded-xl px-6 py-8 space-y-6 max-w-md w-full">
                    <h3 className="text-3xl font-extrabold mb-12 text-center">Sign in</h3>

                    <div>
                        <input
                            name="email"
                            type="email"
                            autoComplete="email"
                            required
                            className="bg-gray-100 focus:bg-transparent w-full text-sm px-4 py-3.5 rounded-md outline-gray-800"
                            placeholder="Email address"
                            onChange={Cemail}
                        />
                    </div>
                    <div>
                        <input
                            name="password"
                            type="password"
                            autoComplete="current-password"
                            required
                            className="bg-gray-100 focus:bg-transparent w-full text-sm px-4 py-3.5 rounded-md outline-gray-800"
                            placeholder="Password"
                            onChange={Cpass}
                        />
                    </div>
                    <div className="text-sm text-right">
                        <Link to="/user/forgot-password" className="text-blue-600 font-semibold hover:underline">
                            Forgot your password?
                        </Link>
                    </div>
                    <div>
                        <button
                            type="button"
                            className="w-full shadow-xl py-3 px-6 text-sm font-semibold rounded-md text-white bg-gray-800 hover:bg-[#222] focus:outline-none"
                            onClick={handleLogin}
                        >
                            Log in
                        </button>
                    </div>

                    <p className="text-sm mt-6 text-black text-center">
                        Don't have an account?
                        <Link to="/user/register" className="text-red-500 font-semibold underline ml-1">
                            Register here
                        </Link>
                    </p>
                </form>
            </div>
        </div>
    )
}

export default Login;
