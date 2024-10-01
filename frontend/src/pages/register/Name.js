import React, { useState } from 'react';

const Name = ({ formData, setFormData, Verify }) => {
  const handleNameChange = (event) => {
    setFormData({
      ...formData,
      firstname: event.target.value,
    });
  };

  const handleLastNameChange = (event) => {
    setFormData({
      ...formData,
      lastname: event.target.value,
    });
  };

  return (
      <div className="flex flex-col items-center text-gray-800">
        <h1 className="text-2xl font-semibold mb-4 text-center">What's your first and last name?</h1>
        <p className="info-text text-sm mb-6 text-center text-gray-600">
          We are happy to have you with us. Let's get to know you better.
        </p>
        <div className="w-full max-w-sm">
          <label className="block mb-2 font-medium">First name*</label>
          <input
              type="text"
              value={formData.firstname}
              onChange={handleNameChange}
              placeholder="first name"
              className="w-full p-3 border border-gray-300 rounded-lg  focus:outline-none focus:border-blue-400"
          />
          {Verify.firstnameVer && <label className='text-sm text-red-500 mb-5'>Please enter your first name</label>}

          <label className="block mb-2 font-medium">Last name*</label>
          <input
              type="text"
              value={formData.lastname} // Make sure this is 'lastname' not 'lastName'
              onChange={handleLastNameChange}
              placeholder="last name"
              className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-400"
          />
          {Verify.lastnameVer && <label className='text-sm text-red-500'>Please enter your last name</label>}
        </div>
      </div>
  );
};

export default Name;
