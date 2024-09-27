import React from 'react';
import herofood1 from '../assets/root/herofood1.jpg';
import herofood2 from '../assets/root/herofood2.png';
import Navbar from '../components/navbar';
import checkmark from '../assets/root/checkmark.svg';
import icon1 from '../assets/root/diary.e6e4c438.svg';
import icon2 from '../assets/root/barcode.18277ae4.svg'; 
import customer1 from '../assets/root/customer1.jpg';
import customer2 from '../assets/root/customer2.jpg';
const Root = () => {
  return (
    <div >
      <nav>
        <Navbar />
      </nav>
      <div className='padding-x'>
      {/* Hero Section */}
      <section className='flex flex-col lg:flex-row items-center justify-center py-20 bg-gradient-to-r '>
        <div className='w-full h-auto lg:w-1/2 flex flex-col items-center text-center lg:text-left'>
          <h1 className='font-extrabold font-protest text-8xl lg:text-5xl text-gray-900 leading-snug'>
            <span className='text-orange-500'>Healthy</span> body, <br />
            <span className='text-orange-500'>Healthy</span> mind.
          </h1>
          <div className='mt-10'>
            <p className='mt-4 text-gray-700 text-lg lg:text-xl'>
              Want to take better control of your eating habits? <br />
              Track your meals, gain insights into your eating patterns, <br />
              and reach your goals with <span className="font-bold text-orange-500">Foody</span>.
            </p>
            <div className='mt-6 flex justify-center space-x-4'>
              <a href="#" className='px-4 py-2 bg-orange-500 text-white rounded-lg shadow hover:bg-orange-600 transition'>Get Started</a>
              <a href="#" className='px-4 py-2 bg-white text-orange-500 border border-orange-500 rounded-lg shadow hover:bg-orange-50 transition'>Learn More</a>
            </div>
          </div>
        </div>

        <div className='w-full lg:w-1/2 flex justify-center lg:justify-start mt-8 lg:mt-0'>
          <img 
            src={herofood1}
            width={700}
            className='rounded-bl-full shadow-lg'
            alt="Healthy food"
          />
        </div>
      </section>

      {/* Nutrition Section */}
      <h2 className='text-4xl font-kanit font-bold text-gray-900 text-center mt-10'>Discover <span className='text-orange-500'>Your</span>  Nutrition</h2>
      <section className='flex flex-col lg:flex-row items-center justify-between py-5 '>
      <div className='w-full lg:w-1/2 flex justify-center mt-8 lg:mt-0'>
          <img 
            src={herofood2} // Use the new image here
            width={600}
            className='rounded-lg'
            alt="Nutrition overview"
          />
        </div>
        <div className='w-full lg:w-1/2 flex flex-col items-start text-left'>
          <p className='text-xl font-Roboto  text-gray-700 font-bold mb-10'>
            Cronometer encourages you to not just count your calories but to focus on your nutrition as a whole.
          </p>
          <ul className='space-y-4 items-center'>
            <li className='flex items-center text-xl  text-gray-700'>
              <img
              src={checkmark}
              width={50}
              />
              <div className='ml-5 mt-7'>
              <h3 className='mt-3 text-xl font-Roboto  text-gray-700 font-bold'>Accurate nutrition data  </h3>
              <span className=' info-text '> - Be confident that the food you log has the correct nutrition data.</span>
              </div>
              </li>
            <li className='flex items-center text-xl  text-gray-700'>
            <img
              src={checkmark}
              width={50}
              /> 
              <div className='ml-5 mt-5 '>
              <h3 className='text-xl font-Roboto  text-gray-700 font-bold'>Data privacy & security </h3>
              <span className='info-text '> - We don’t sell your account data to third parties.</span>
              </div>
            </li>
          </ul>
        </div>
      </section>

      {/*Tools for Your */}
      <section className="py-20 px-6 text-center">
      <h2 className="text-4xl font-bold text-gray-900 mb-4">Tools for Your <span className='text-orange-500'>Goals</span></h2>
      <p className="text-xl font-Roboto  text-gray-700 font-bold mb-10">
        Trying to lose weight, tone up, lower your BMI, or improve your general health? 
        We provide the right tools to help you achieve your goals.
      </p>

      <div className="flex grid-cols-1 md:grid-cols-3 gap-10">
        {/* First Tool */}
        <div className="flex flex-col items-center">
          <img
            src={icon1}
            alt="Learn, Track, Progress"
            width={200}
            className="mb-4"
          />
          <h3 className="text-xl font-Roboto  text-gray-700 font-bold">Learn. Track. Progress.</h3>
          <p className="text-gray-600 mt-2">
            Keeping a food journal helps you better understand your habits and increases your chances of reaching your goals.
          </p>
        </div>

        {/* Second Tool */}
        <div className="flex flex-col items-center">
          <img
            src={icon2}
            width={200}
            className="mb-4"
          />
          <h3 className="text-xl font-bold text-gray-900">Log food easily.</h3>
          <p className="text-gray-600 mt-2">
            Scan barcodes, log meals and recipes, and use quick tools for fast and easy food tracking.
          </p>
        </div>

      </div>
    </section>
    
    {/*Customer Reviews */}
    <section className="flex flex-col py-10 px-6 text-center pb-20">
  <h2 className="text-4xl font-bold text-gray-900 mb-10">Customer Reviews</h2>

  <div className="grid grid-cols-1 gap-8 md:grid-cols-2">
    {/* customer1 Review */}
    <div className="bg-white shadow-lg rounded-lg p-6 text-left flex flex-col">
      <div className="text-center mb-6">
        <img
          src={customer1}
          alt="Customer 1"
          className="mx-auto w-24 h-24 rounded-full object-cover"
        />
      </div>
      <div>
        <h3 className="text-xl font-semibold text-teal-600 mb-2">
          A great all-in-one health app!
        </h3>
        <p className="text-gray-600">
          After leaving SparkPeople, I came here. I love that it is Canadian, includes fasting, can import recipes, gives me macro details, and more. This is my daily tracking app.
        </p>
      </div>
    </div>

    {/* customer2 Review */}
    <div className="bg-white shadow-lg rounded-lg p-6 text-left flex flex-col justify-between">
      <div className="text-center mb-4">
        <img
          src={customer2}
          alt="Customer 2"
          className="mx-auto w-24 h-24 rounded-full object-cover"
        />
      </div>
      <div>
        <p className="text-gray-600">
          Awesome. Getting direct feedback when you track your food really opens your eyes to what you’re putting in your body. The modularity and ability to track macros, micros, and calories in-calories out makes this an invaluable tool for any nutritional goal.
        </p>
        <h3 className="text-xl font-semibold text-teal-600 mt-4">
          Whether that's bodybuilding, weight loss, endurance training, or just maintenance – everyone should be using Foody.
        </h3>
      </div>
    </div>
  </div>
</section>

      </div>
      <footer className="bg-gray-900 py-10">
  <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
      <div className="col-span-1 text-center md:text-left">
        <h1 className="text-4xl protest-guerrilla-regular tracking-wide text-gray-100">
          <span className="text-red-600">FOODY</span>
        </h1>
      </div>

      <div className="col-span-1 text-center md:text-left">
        <h3 className="text-lg font-semibold text-gray-100 mb-3">The Product</h3>
        <ul className="space-y-2">
          <li><a href="#" className="text-gray-400 hover:text-white">For Individuals</a></li>
          <li><a href="#" className="text-gray-400 hover:text-white">For Professionals</a></li>
          <li><a href="#" className="text-gray-400 hover:text-white">Privacy</a></li>
          <li><a href="#" className="text-gray-400 hover:text-white">Terms</a></li>
        </ul>
      </div>

      <div className="col-span-1 text-center md:text-left">
        <h3 className="text-lg font-semibold text-gray-100 mb-3">The Company</h3>
        <ul className="space-y-2">
          <li><a href="#" className="text-gray-400 hover:text-white">About Us</a></li>
          <li><a href="#" className="text-gray-400 hover:text-white">Blog</a></li>
          <li><a href="#" className="text-gray-400 hover:text-white">Forums</a></li>
          <li><a href="#" className="text-gray-400 hover:text-white">Jobs</a></li>
          <li><a href="#" className="text-gray-400 hover:text-white">Support</a></li>
        </ul>
      </div>

      <div className="col-span-1 text-center md:text-left">
        <h3 className="text-lg font-semibold text-gray-100 mb-3">Partners & Affiliates</h3>
        <ul className="space-y-2">
          <li><a href="#" className="text-gray-400 hover:text-white">Affiliate Program</a></li>
          <li><a href="#" className="text-gray-400 hover:text-white">Media Kit</a></li>
        </ul>
      </div>
    </div>
  </div>
</footer>
    </div>
  );
};

export default Root;
