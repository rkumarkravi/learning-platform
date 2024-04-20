import React from "react";
import { useSelector } from "react-redux";

const ViewProfile: React.FC = () => {
const data = useSelector((state: any) => state.userProfile.value);
  return (
    <div>
      <h2>User Profile</h2>
      <p>
        <strong>User ID:</strong> {data.userId}
      </p>
      <p>
        <strong>Username:</strong> {data.username}
      </p>
      <p>
        <strong>Email:</strong> {data.email}
      </p>
      <p>
        <strong>Full Name:</strong> {data.fullName}
      </p>
      <p>
        <strong>Phone Number:</strong> {data.phoneNumber}
      </p>
      <p>
        <strong>Interests:</strong>
      </p>
      <ul>
        {data.interests.map((interest, index) => (
          <li key={index}>{interest}</li>
        ))}
      </ul>
      <p>
        <strong>Role:</strong> {data.role}
      </p>
      {/* Include additional fields as needed */}
    </div>
  );
};

export default ViewProfile;
