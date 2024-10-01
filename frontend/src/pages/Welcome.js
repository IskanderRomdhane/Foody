import React from 'react';
import first from '../assets/welcome/first.webp';
import middle from '../assets/welcome/middle.png';
import third from '../assets/welcome/mealindex.webp';
import { useNavigate } from 'react-router-dom';

const Welcome = () => {
    const navigate = useNavigate();

    return (
        <div className="h-screen flex flex-col justify-center items-center px-[250px]">
            {/* Title */}
            <h1 className="text-2xl font-semibold mb-8 text-center font-light font-Roboto">
                Welcome to <br />
                <span>
                    <h1 className='text-4xl protest-guerrilla-regular tracking-wide text-gray-800'>
                        <span className='text-red-600'>FOODY</span>
                    </h1>
                </span>
            </h1>

            {/* Three images section */}
            <div className="flex justify-center gap-16 mb-8 font-Roboto sm:flex flex-wrap">
                {/* First Image */}
                <div className="flex flex-col items-center text-center w-[300px]">
                    <img
                        className="rounded-lg"
                        src={first}
                        width={300}
                        alt="Protein tracking"
                    />
                    <p className="mt-4 text-lg">
                        Ready to achieve your fitness goals? Start tracking your protein intake today!
                    </p>
                </div>

                {/* Middle Image */}
                <div className="flex flex-col items-center text-center w-[300px]">
                    <img
                        className="rounded-lg"
                        src={middle}
                        width={300}
                        alt="Calories tracking"
                    />
                    <p className="mt-16 text-lg">
                        Explore how your meals impact your health and fitness. Make informed choices!
                    </p>
                </div>

                {/* Third Image */}
                <div className="flex flex-col items-center text-center w-[300px]">
                    <img
                        className="rounded-lg"
                        src={third}
                        width={300}
                        alt="Meal nutrition"
                    />
                    <p className="mt-7 text-lg">
                        Learn to eat mindfully. Make balanced choices for a healthier lifestyle!
                    </p>
                </div>
            </div>

            {/* Continue button */}
            <button
                className="bg-blue-500 text-white py-2 px-8 rounded-full font-semibold"
                onClick={() => navigate("/user/home")}
            >
                CONTINUE
            </button>
        </div>
    );
};

export default Welcome;
