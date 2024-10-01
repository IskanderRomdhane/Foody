import React, { useState } from 'react';

const PersonalDetails = ({ formData, setFormData , Verify }) => {
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);
  const [passwordError, setPasswordError] = useState('');
  const [confirmPasswordError, setConfirmPasswordError] = useState('');

  const handleEmailChange = (event) => {
    setFormData({ ...formData, email: event.target.value });
  };

  const handlePasswordChange = (event) => {
    const newPassword = event.target.value;
    setFormData({ ...formData, password: newPassword });

    if (newPassword.length < 8 || !/[A-Z]/.test(newPassword)) {
      setPasswordError('Password must be at least 8 characters long and contain at least one uppercase letter.');
    } else {
      setPasswordError('');
    }

    if (formData.confirmPassword && newPassword !== formData.confirmPassword) {
      setConfirmPasswordError('Passwords do not match.');
    } else {
      setConfirmPasswordError('');
    }
  };

  const handleConfirmPasswordChange = (event) => {
    const newConfirmPassword = event.target.value;
    setFormData({ ...formData, confirmPassword: newConfirmPassword });

    if (newConfirmPassword !== formData.password) {
      setConfirmPasswordError('Passwords do not match.');
    } else {
      setConfirmPasswordError('');
    }
  };

  const togglePasswordVisibility = () => {
    setPasswordVisible((prev) => !prev);
  };

  const toggleConfirmPasswordVisibility = () => {
    setConfirmPasswordVisible((prev) => !prev);
  };

  return (
      <div className="flex flex-col items-center text-gray-800">
        <h1 className="text-2xl font-semibold mb-4 text-center">Enter Your Email and Password</h1>
        <p className="info-text text-sm mb-6 text-center text-gray-600">
          We are happy to have you with us. Please provide your email and create a secure password.
        </p>
        <div className="w-full max-w-sm">
          <label className="block mb-2 font-medium">Email</label>
          <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleEmailChange}
              placeholder="Enter your email"
              className="w-full p-3 border border-gray-300 rounded-lg mb-5 focus:outline-none focus:border-blue-400"
          />
          <label className="block mb-2 font-medium">Password</label>
          <div className="relative">
            <input
                type={passwordVisible ? 'text' : 'password'}
                name="password"
                value={formData.password}
                onChange={handlePasswordChange}
                placeholder="Create a password"
                className="w-full p-3 border border-gray-300 rounded-lg mb-2 focus:outline-none focus:border-blue-400"
            />
            <button
                type="button"
                onClick={togglePasswordVisibility}
                className="absolute right-2 top-3 text-gray-600"
            >
              {passwordVisible ? 'Hide' : 'Show'}
            </button>
          </div>
          {passwordError && <p className="text-red-500 text-sm">{passwordError}</p>}
          <label className="block mb-2 font-medium">Confirm Password</label>
          <div className="relative">
            <input
                type={confirmPasswordVisible ? 'text' : 'password'}
                name="confirmPassword"
                value={formData.confirmPassword}
                onChange={handleConfirmPasswordChange}
                placeholder="Confirm your password"
                className="w-full p-3 border border-gray-300 rounded-lg mb-2 focus:outline-none focus:border-blue-400"
            />
            <button
                type="button"
                onClick={toggleConfirmPasswordVisibility}
                className="absolute right-2 top-3 text-gray-600"
            >
              {confirmPasswordVisible ? 'Hide' : 'Show'}
            </button>
          </div>
          {confirmPasswordError && <p className="text-red-500 text-sm">{confirmPasswordError}</p>}
        </div>
      </div>
  );
};

export default PersonalDetails;
