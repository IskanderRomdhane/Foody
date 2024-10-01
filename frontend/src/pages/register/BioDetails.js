import React from 'react';

const BioDetails = ({ formData, setFormData, Verify }) => {
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  return (
      <div className="flex flex-col items-center text-gray-800">
        <h1 className="text-2xl font-semibold mb-4 text-center">Tell us more about yourself</h1>
        <p className="text-sm mb-6 text-center text-gray-600">We are happy to have you with us. Let's get to know you better.</p>
        <div className="w-full max-w-sm">
          <label className="block mb-2 font-medium">Date of Birth</label>
          <input
              type="date"
              name="date_of_birth"
              value={formData.date_of_birth}
              onChange={handleInputChange}
              className="w-full p-3 border border-gray-300 rounded-lg mb-5 focus:outline-none focus:border-blue-400"
          />
          <label className="block mb-2 font-medium">Age</label>
          <input
              type="number"
              name="age"
              value={formData.age}
              onChange={handleInputChange}
              placeholder="Age"
              className="w-full p-3 border border-gray-300 rounded-lg mb-5 focus:outline-none focus:border-blue-400"
          />
          {Verify.ageVer && <label className='text-sm text-red-500'>Please enter your Age</label>}
          <label className="block mb-2 font-medium">Weight (kg)</label>
          <input
              type="number"
              name="weight"
              value={formData.weight}
              onChange={handleInputChange}
              placeholder="Weight in kg"
              className="w-full p-3 border border-gray-300 rounded-lg mb-5 focus:outline-none focus:border-blue-400"
          />
          {Verify.weightVer && <label className='text-sm text-red-500'>Please enter your Weight</label>}
          <label className="block mb-2 font-medium">Height (cm)</label>
          <input
              type="number"
              name="height"
              value={formData.height}
              onChange={handleInputChange}
              placeholder="Height in cm"
              className="w-full p-3 border border-gray-300 rounded-lg mb-5 focus:outline-none focus:border-blue-400"
          />
          {Verify.heightVer && <label className='text-sm text-red-500'>Please enter your Height</label>}
          <label className="block mb-2 font-medium">Gender</label>
          <select
              name="gender"
              value={formData.gender}
              onChange={handleInputChange}
              className="w-full p-3 border border-gray-300 rounded-lg mb-5 focus:outline-none focus:border-blue-400"
          >
            {Verify.genderVer && <label className='text-sm text-red-500'>Please enter your Gender</label>}
            <option value="">Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
        </div>
      </div>
  );
};

export default BioDetails;
