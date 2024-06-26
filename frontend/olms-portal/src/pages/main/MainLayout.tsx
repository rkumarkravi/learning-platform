import { Outlet, useLocation } from "react-router-dom";
import { Menus } from "./main-comps/Menus";
import { useDispatch, useSelector } from "react-redux";
import { ModeToggle } from "@/reusables/ModeToggle";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { put } from "@/store/newway/user-profile-slice";
// import { setUserProfile } from "@/store/oldway/LmsActions";
// import { RootState } from "@/store/oldway/LmsReducers";

function MainLayout() {
  const userProfile = useSelector((state: any) => state.userProfile.value);
  // const dispatch = useDispatch();
  // const { state } = useLocation();

  // if (state && state.rs === "S") {
   
  // }
  function getAvatarFallBack() {
    if (userProfile && userProfile.fullName)
      return userProfile.fullName
        .split(" ")
        .map((x) => x[0])
        .join("");
    else return "";
  }
  // const [expanded, setExpanded] = useState(true);

  // // const toggleExpand = () => {
  // //   setExpanded(!expanded);
  // // };

  return (
    <div className="flex flex-col">
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

      <div className="flex justify-between p-2 gap-1">
        <div className="flex gap-2">
          <Avatar>
            <AvatarFallback>{getAvatarFallBack()}</AvatarFallback>
          </Avatar>

          <Menus className="w-min" />
        </div>
        <ModeToggle />
      </div>
      <div className="min-h-lvh p-2">
        <Outlet />
      </div>
    </div>
  );
}

export default MainLayout;
