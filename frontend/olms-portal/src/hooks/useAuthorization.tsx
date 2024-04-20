import { useState, useEffect } from "react";
import axiosService from "@/services/Axios";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { ApiResponse } from "@/models/Response";
import { put, remove } from "@/store/newway/user-profile-slice";

// Custom hook to check if user is authorized
const useAuthorization = () => {
  const [isAuthorized, setIsAuthorized] = useState(false);
  const [loading, setLoading] = useState(true);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  useEffect(() => {
    // Function to check authorization
    const checkAuthorization = async () => {
      try {
        console.warn("Auth Hook Called!")
        // Call your API endpoint to check if the token is valid
        if (!(localStorage.getItem("at") && localStorage.getItem("rt"))) {
          localStorage.removeItem("at");
          localStorage.removeItem("rt");
          dispatch(remove());
          navigate("/");
          return;
        }
        const response: ApiResponse = await axiosService(
          "POST",
          "/auth/validTkn",
          {}
        );

        // If the token is valid, set isAuthorized to true
        if (response.rs === "S") {
          dispatch(put(response.payload.userDetails));
          setIsAuthorized(true);
          navigate("/main")
        } else {
          navigate("/");
          setIsAuthorized(false);
        }
      } catch (error) {
        // If there's an error (e.g., network error, token expired), set isAuthorized to false
        setIsAuthorized(false);
      } finally {
        // Set loading to false once the check is complete
        setLoading(false);
      }
    };

    // Call the function to check authorization when component mounts
    checkAuthorization();

    // Cleanup function (optional)
    return () => {
      // Perform any cleanup if needed
    };
  }, []);

  return { isAuthorized, loading };
};

export default useAuthorization;
