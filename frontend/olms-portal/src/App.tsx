import { Outlet, useNavigate } from "react-router-dom";
import "./App.css";
import { ToastProvider } from "./components/ui/toast";
import { Toaster } from "./components/ui/toaster";
import axios from "axios";
import { toast } from "./components/ui/use-toast";

function App() {
  const navigate = useNavigate();

  axios.interceptors.response.use(
    (response) => {
      if (
        response.status == 200 &&
        response.data &&
        response.data.rs &&
        response.data.rs === "F"
      ) {
        toast({
          variant: "destructive",
          title: "API Error",
          description: response.data.rd,
        });
      }
      return response;
    },
    (error) => {
      // console.info("http response:", error);
      toast({
        variant: "destructive",
        title: "Error",
        description:
          error.response && error.response.data
            ? error.response.data.rd
            : error.message,
      });
      // Check if the error status is 401 (Unauthorized)
      if (error.response && error.response.status === 401) {
        // Redirect to the sign-in page
        localStorage.removeItem("at");
        navigate("/");
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
