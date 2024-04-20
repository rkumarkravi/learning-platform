import useAuthorization from "@/hooks/useAuthorization";
import { Navigate } from "react-router-dom";

function ProtectedRoute({ element }) {
  const { isAuthorized } = useAuthorization();
  console.log("ProtectedRoute",isAuthorized)
  return isAuthorized ? element : <h1>Loading...</h1>;
  //<Navigate to="/dssd" replace />
}

export default ProtectedRoute;
