import React from "react";
import { useSelector } from "react-redux";
import StudentDashboard from "./student-comps/dashboard/StudentDashboard";
import TeacherDashboard from "./teacher-comps/dashboard/TeacherDashboard";
import { Navigate } from "react-router-dom";

function MainDashboard() {
  const userProfile = useSelector((state: any) => state.userProfile.value);
  const renderDashboard = () => {
    if (userProfile) {
      const userRole = userProfile.role;
      console.log("MainDashboard",userRole)
      switch (userRole) {
        // case "ADMIN":
        //   return <AdminDashboard />;
        case "STUDENT":
          return <StudentDashboard />;
        case "TEACHER":
          return <TeacherDashboard />;
        default:
          return <Navigate to="/" replace />;
      }
    }
  };
  return renderDashboard();
}

export default MainDashboard;
