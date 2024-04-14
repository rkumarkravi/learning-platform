import { Outlet, useNavigate } from "react-router-dom";
import "./App.css";
import { ToastProvider } from "./components/ui/toast";
import { Toaster } from "./components/ui/toaster";
import axios from "axios";

function App() {
  const navigate=useNavigate();

  axios.interceptors.response.use(
    response => {
      return response;
    },
    error => {
      // Check if the error status is 401 (Unauthorized)
      if (error.response && error.response.status === 401) {
        // Redirect to the sign-in page
        localStorage.removeItem("at");
        navigate('/');
      }
      return Promise.reject(error);
    }
  );

  return (
    <div>
      <ToastProvider>
        <Outlet />
        <Toaster />
      </ToastProvider>
    </div>
  );
}

export default App;
