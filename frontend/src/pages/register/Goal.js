import React, { useState } from 'react';

const Goal = ({ formData, setFormData, Verify }) => {
  const [selectedGoal, setSelectedGoal] = useState(null);

  const goals = [
    { id: 1, text: 'maintainWeight', display: 'Weight Loss' },
    { id: 2, text: 'gradualGain', display: 'Muscle Gain' },
    { id: 3, text: 'extremeGain', display: 'Maintain Weight' },
    { id: 4, text: 'gradualLoss', display: 'Improve Endurance' },
    { id: 5, text: 'extremeLoss', display: 'Increase Flexibility' },
  ];

  const handleGoalSelect = (goalId) => {
    setSelectedGoal(goalId);
    setFormData({ ...formData, goal: goals.find((goal) => goal.id === goalId).text });
    console.log(formData);
  };

  return (
      <div className="flex flex-col justify-center items-center">
        <h2 className="text-2xl font-bold mb-2 text-gray-800">Welcome {formData.firstname}, Select Your Goal</h2>
        <p className="text-sm text-gray-600 mb-4">Choose the option that best reflects your fitness aspirations.</p>

        <div className="flex flex-col gap-4">
          {goals.map((goal) => (
              <div
                  key={goal.id}
                  className={`border-2 rounded-md p-5 text-center cursor-pointer transition-transform duration-300 ease-in-out shadow-md w-[400px] ${
                      selectedGoal === goal.id
                          ? 'border-green-500 bg-green-100 transform scale-105'
                          : 'border-gray-300 hover:border-gray-600 hover:bg-gray-100'
                  }`}
                  onClick={() => handleGoalSelect(goal.id)}
              >
                {goal.display}

              </div>
          ))}
            {Verify.goalVer && <p className='text-sm text-red-500'>Please select a goal to continue.</p>}
        </div>
      </div>
  );
};

export default Goal;
