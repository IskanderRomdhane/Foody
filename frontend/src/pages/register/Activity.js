import React, { useState } from 'react';

const Activity = ({ formData, setFormData , Verify }) => {
  const [selectedActivity, setSelectedActivity] = useState(null);

  const activityLevels = [
    { id: 1, text: 'Sedentary', display: 'Sedentary (Little to no exercise)' },
    { id: 2, text: 'Lightly Active', display: 'Lightly Active (Light exercise/sports 1-3 days/week)' },
    { id: 3, text: 'Moderately Active', display: 'Moderately Active (Moderate exercise/sports 3-5 days/week)' },
    { id: 4, text: 'Very Active', display: 'Very Active (Hard exercise/sports 6-7 days/week)' },
    { id: 5, text: 'Super Active', display: 'Super Active (Very hard exercise/sports & physical job)' },
  ];

  const handleActivitySelect = (activityId) => {
    setSelectedActivity(activityId);
    setFormData({ ...formData, activityLevel: activityLevels.find((activity) => activity.id === activityId).text });
  };

  return (
      <div className="flex justify-center items-center">
        <form>
          <h2 className="text-2xl font-bold mb-2 text-gray-800">Select Your Activity Level</h2>
          <p className="text-sm text-gray-600 mb-4">Choose the option that best reflects your daily activity.</p>
          <div className="flex flex-col gap-4">
            {activityLevels.map((activity) => (
                <div
                    key={activity.id}
                    className={`border-2 rounded-md p-5 text-center cursor-pointer transition-transform duration-300 ease-in-out shadow-md w-[400px] ${
                        selectedActivity === activity.id
                            ? 'border-green-500 bg-green-100 transform scale-105'
                            : 'border-gray-300 hover:border-gray-600 hover:bg-gray-100'
                    }`}
                    onClick={() => handleActivitySelect(activity.id)}
                >
                  {activity.display}
                </div>
            ))}
          </div>
          {Verify.goalVer && <p className='text-sm text-red-500'>Please select a goal to continue.</p>}
        </form>

      </div>
  );
};

export default Activity;
