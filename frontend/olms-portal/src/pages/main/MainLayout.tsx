import { useData } from "@/main";
import { ApiResponse } from "@/models/Response";
import axiosService from "@/services/Axios";
import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

function MainLayout() {
  const { data, setData } = useData();

  const navigate = useNavigate();
  const { state } = useLocation();
  useEffect(() => {
    if (state && state.rs === "S") {
      localStorage.setItem("at", state.payload.at);
      localStorage.setItem("rt", state.payload.rt);
       setData({ ...data, userDetail: state.payload });
      return;
    }
    if (!(localStorage.getItem("at") && localStorage.getItem("rt"))) {
      localStorage.removeItem("at");
      localStorage.removeItem("rt");
      setData({ ...data, loader: false });
      navigate("/");
      return;
    } else {
      // const at = localStorage.getItem("at");
      // console.log("at iis ::", at);
      // const auth = { "Authorization": `Bearer ${at}` };
      axiosService("POST", "/auth/validTkn", {}).then((x: ApiResponse) => {
        if (x && x.rs === "F") {
          navigate("/");
          return;
        } else {
          if (!data.userDetail) {
            x.payload.userDetails.auth = "MAIN";
            setData({ ...data, userDetail: x.payload.userDetails });
          }
        }
      });
    }
  }, []);
  return (
    <div className="flex">
      <div className="w-1/5 bg-black h-screen flex flex-col">
        
      </div>
      <div className="flex-1">{JSON.stringify(data)}</div>
    </div>
  );
}

export default MainLayout;
