import useAuthorization from "@/hooks/useAuthorization";
import { Navigate } from "react-router-dom";

function ProtectedRoute({ element }) {
  const { isAuthorized,loading } = useAuthorization();
  console.log("ProtectedRoute",isAuthorized)
  if (loading) {
    // Render loading indicator while authorization status is being checked
    return <div>Loading...</div>;
  }
  return isAuthorized ? element : <Navigate to="/" replace />;
  //<Navigate to="/dssd" replace />
}

export default ProtectedRoute;
