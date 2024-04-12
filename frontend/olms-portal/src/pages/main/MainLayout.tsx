import { ApiResponse } from "@/models/Response";
import axiosService from "@/services/Axios";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import MainStudent from "./MainStudent";
import MainTeacher from "./MainTeacher";
import { Menus } from "./main-comps/Menus";
import { useDispatch, useSelector } from "react-redux";
import { put, remove } from "@/store/newway/user-profile-slice";
// import { setUserProfile } from "@/store/oldway/LmsActions";
// import { RootState } from "@/store/oldway/LmsReducers";

function MainLayout() {
  const userProfile = useSelector((state: any) => state.userProfile.value);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { state } = useLocation();
  useEffect(() => {
    if (state && state.rs === "S") {
      localStorage.setItem("at", state.payload.at);
      localStorage.setItem("rt", state.payload.rt);
      dispatch(put(state.payload));
      return;
    }
    if (!(localStorage.getItem("at") && localStorage.getItem("rt"))) {
      localStorage.removeItem("at");
      localStorage.removeItem("rt");
      dispatch(remove());
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
          if (!userProfile) {
            x.payload.userDetails.auth = "MAIN";
            dispatch(put(x.payload.userDetails));
          }
        }
      });
    }
  }, [dispatch]);

  function getLayoutByRole() {
    if (userProfile && userProfile.role === "TEACHER")
      return <MainTeacher />;
    else return <MainStudent />;
  }

  // const [expanded, setExpanded] = useState(true);

  // // const toggleExpand = () => {
  // //   setExpanded(!expanded);
  // // };

  return (
    <div>
      {/* <div
        className={cn(
          `${
            expanded ? "w-1/5" : "min-w-16"
          } h-screen flex flex-col shadow-lg p-4 relative`,
          theme === "dark" && "shadow-sm shadow-white/100"
        )}
      >
        <Button
          variant="outline"
          size="icon"
          className="absolute top-1 -right-3 rounded-full h-6 w-6"
          onClick={toggleExpand}
        >
          {expanded && <ChevronRight size={20}/>}
          {!expanded && <ChevronLeft size={20}/>}
        </Button>
        <ModeToggle />
      </div> */}
      <div className="absolute top-1 left-1">
        <Menus/>
      </div>
      <div className="min-h-lvh">{getLayoutByRole()}{JSON.stringify(userProfile)}</div>
    </div>
  );
}

export default MainLayout;
