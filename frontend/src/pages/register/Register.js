import React, { useState } from 'react';
import Name from './Name';
import Goal from './Goal';
import Activity from './Activity';
import BioDetails from './BioDetails';
import PersonalDetails from './PersonalDetails';
import { useNavigate } from 'react-router-dom';
import registerService from '../../services/auth/registerService';

const Register = () => {
  const navigate = useNavigate();
  const [step, setStep] = useState(1);
  const totalSteps = 5;
  const [formData, setFormData] = useState({
    firstname: '',
    lastname: '',
    email: '',
    password: '',
    weight: '',
    height: '',
    age: '',
    gender: '',
    goal: '',
    activityLevel: ''
  });

  const [verify, setVerify] = useState({
    firstnameVer: false,
    lastnameVer: false,
    goalVer: false,
    activityVer: false,
    ageVer: false,
    weightVer: false,
    heightVer: false,
    genderVer: false,
    emailVer: false,
    passwordVer: false
  });

  const nextStep = () => {
    if (step === 1) {
      if (formData.firstname === '') {
        setVerify((prev) => ({ ...prev, firstnameVer: true }));
        return;
      } else {
        setVerify((prev) => ({ ...prev, firstnameVer: false }));
      }
      if (formData.lastname === '') {
        setVerify((prev) => ({ ...prev, lastnameVer: true }));
        return;
      } else {
        setVerify((prev) => ({ ...prev, lastnameVer: false }));
      }
    }

    if (step === 2) {
      if (formData.goal === '') {
        setVerify((prev) => ({ ...prev, goalVer: true }));
        return;
      }
    }
    if (step === 3) {
      if (formData.activityLevel === '') {
        setVerify((prev) => ({ ...prev, activityVer: true }));
        return;
      }
    }

    if (step === 4) {
      if (formData.age === "") {
        setVerify((prev) => ({ ...prev, ageVer: true }));
        return;
      }
      if (formData.weight === "") {
        setVerify((prev) => ({ ...prev, weightVer: true }));
        return;
      }
      if (formData.height === "") {
        setVerify((prev) => ({ ...prev, heightVer: true }));
        return;
      }
      if (formData.gender === "") {
        setVerify((prev) => ({ ...prev, genderVer: true }));
        return;
      }
    }

    setStep(step + 1);
  };

  const prevStep = () => setStep(step - 1);

  const handleRegister = async () => {
    // Password validation
    const passwordValid = formData.password.length >= 8 && /[A-Z]/.test(formData.password);
    const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email);

    if (!passwordValid) {
      setVerify((prev) => ({ ...prev, passwordVer: true }));
      return;
    }

    if (!emailValid) {
      setVerify((prev) => ({ ...prev, emailVer: true }));
      return;
    }

    const requestData = {
      firstname: formData.firstname,
      lastname: formData.lastname,
      email: formData.email,
      password: formData.password,
      weight: Number(formData.weight),
      height: Number(formData.height),
      age: Number(formData.age),
      gender: formData.gender,
      goal: formData.goal,
      activityLevel: formData.activityLevel
    };

    try {
      console.log(requestData);
      const result = await registerService(requestData);
      console.log(result);
      navigate('/user/welcome');
    } catch (error) {
      console.log(error);
    }
  };

  const renderStep = () => {
    switch (step) {
      case 1:
        return <Name formData={formData} setFormData={setFormData} Verify={verify} />;
      case 2:
        return <Goal formData={formData} setFormData={setFormData} Verify={verify} />;
      case 3:
        return <Activity formData={formData} setFormData={setFormData} Verify={verify} />;
      case 4:
        return <BioDetails formData={formData} setFormData={setFormData} Verify={verify} />;
      case 5:
        return <PersonalDetails formData={formData} setFormData={setFormData} Verify={verify} />;
      default:
        return <Goal formData={formData} setFormData={setFormData} Verify={verify} />;
    }
  };

  const getProgressWidth = () => {
    return (step / totalSteps) * 100;
  };

  return (
      <div className="min-h-screen flex flex-col items-center justify-center">
        <h1 className='text-4xl protest-guerrilla-regular tracking-wide text-gray-800'>
          <span className='text-red-600'>FOODY</span>
        </h1>
        <div className="w-[500px] bg-white rounded-lg shadow-lg">
          <div className="relative w-full">
            <div className="absolute top-0 left-0 w-full h-2 bg-gray-200 rounded-t-lg"></div>
            <div
                className="absolute top-0 left-0 h-2 bg-blue-500 rounded-t-lg transition-all duration-300"
                style={{ width: `${getProgressWidth()}%` }}
            ></div>
          </div>

          <div className="p-8">
            {renderStep()}

            <div className="mt-10 mx-8">
              {step < totalSteps ? (
                  <div className='flex justify-between'>
                    <button
                        className="px-4 py-2 text-blue-500 bg-white border border-blue-500 rounded hover:bg-blue-100 transition duration-200 w-[150px] font-bold"
                        onClick={prevStep}
                        disabled={step === 1}
                    >
                      Previous
                    </button>

                    <button
                        className="px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600 transition duration-200 w-[150px]"
                        onClick={nextStep}
                    >
                      Next
                    </button>
                  </div>
              ) : (
                  <div className='flex justify-between'>
                    <button
                        className="px-4 py-2 text-blue-500 bg-white border border-blue-500 rounded hover:bg-blue-100 transition duration-200"
                        onClick={prevStep}
                        disabled={step === 1}
                    >
                      Previous
                    </button>

                    <button
                        className="px-4 py-2 text-white bg-red-500 rounded hover:bg-red-600 transition duration-200"
                        onClick={handleRegister}
                    >
                      Submit
                    </button>
                  </div>
              )}
            </div>
          </div>
        </div>
      </div>
  );
};

export default Register;
