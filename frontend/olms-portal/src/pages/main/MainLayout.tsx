import { Link, Outlet, useLocation } from "react-router-dom";
import { Menus } from "./main-comps/Menus";
import { useDispatch, useSelector } from "react-redux";
import { ModeToggle } from "@/reusables/ModeToggle";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { put } from "@/store/newway/user-profile-slice";
// import { setUserProfile } from "@/store/oldway/LmsActions";
// import { RootState } from "@/store/oldway/LmsReducers";
import Dashboard from "./teacher-comps/dashboard/TeacherDashboard";
import { cn } from "@/lib/utils";
import { useTheme } from "@/components/ThemeProvider";

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
  const location = useLocation();
  const isActive = (path) => {
    return location.pathname === path;
  };
  const { setTheme } = useTheme();
  return (
    <div className="flex flex-col sm:flex-row ">
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

      <div className="w-1/8 justify-start px-2 gap-1 hidden rounded-sm shadow-lg sm:flex flex-col fixed">
        <div className="flex h-screen flex-col justify-between bg-background">
          <div>
            {/* <span className="grid h-10 w-32 place-content-center rounded-lg bg-gray-100 text-xs text-gray-600">
              Logo
            </span> */}

            <ul className="mt-6 space-y-1">
              <li>
                <Link
                  to="/main"
                  className={cn(
                    "block rounded-lg px-4 py-2 text-sm text-left font-medium ",
                    isActive("/main") && "bg-gray-100 text-gray-700"
                  )}
                >
                  Dashboard
                </Link>
              </li>
              {userProfile.role==='TEACHER' && <li>
                <details className="group [&_summary::-webkit-details-marker]:hidden">
                  <summary className="flex cursor-pointer items-center justify-between rounded-lg px-4 py-2 text-gray-500 hover:bg-gray-100 hover:text-gray-700">
                    <span className="text-sm text-left font-medium">
                      Course
                    </span>

                    <span className="shrink-0 transition duration-300 group-open:-rotate-180">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-5 w-5"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                          clip-rule="evenodd"
                        />
                      </svg>
                    </span>
                  </summary>

                  <ul className="mt-2 space-y-1 px-4">
                    <li>
                      <Link
                        to={"createCourse"}
                        state={{ cid: "", mode: "CREATE" }}
                        className={cn(
                          "block rounded-lg px-4 py-2 text-sm text-left font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700",
                          isActive("/main/viewProfile") &&
                            "bg-gray-100 text-gray-700"
                        )}
                      >
                        Create Course
                      </Link>
                    </li>
                  </ul>
                </details>
              </li>}

              <li>
                <details className="group [&_summary::-webkit-details-marker]:hidden">
                  <summary className="flex cursor-pointer items-center justify-between rounded-lg px-4 py-2 text-gray-500 hover:bg-gray-100 hover:text-gray-700">
                    <span className="text-sm text-left font-medium">Theme</span>

                    <span className="shrink-0 transition duration-300 group-open:-rotate-180">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-5 w-5"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                          clip-rule="evenodd"
                        />
                      </svg>
                    </span>
                  </summary>

                  <ul className="mt-2 space-y-1 px-4">
                    <li>
                      <div
                        className="cursor-pointer block rounded-lg px-4 py-2 text-sm text-left font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                        onClick={() => setTheme("light")}
                      >
                        Light
                      </div>
                    </li>
                    <li>
                      <div
                        className="cursor-pointer block rounded-lg px-4 py-2 text-sm text-left font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                        onClick={() => setTheme("dark")}
                      >
                        Dark
                      </div>
                    </li>
                  </ul>
                </details>
              </li>
            </ul>
          </div>

          <div className="sticky inset-x-0 bottom-0 border-t border-gray-100">
            <div className="mt-1">
              <li className="list-none">
                <details className="group [&_summary::-webkit-details-marker]:hidden">
                  <summary className="flex cursor-pointer items-center justify-between rounded-lg px-4 py-2 text-gray-500 hover:bg-gray-100 hover:text-gray-700">
                    <span className="text-sm text-left font-medium">
                      Account
                    </span>

                    <span className="shrink-0 transition duration-300 group-open:-rotate-180">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-5 w-5"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                          clip-rule="evenodd"
                        />
                      </svg>
                    </span>
                  </summary>

                  <ul className="mt-2 space-y-1 px-4">
                    <li>
                      <Link
                        to="viewProfile"
                        className={cn(
                          "block rounded-lg px-4 py-2 text-sm text-left font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700",
                          isActive("/main/viewProfile") &&
                            "bg-gray-100 text-gray-700"
                        )}
                      >
                        Details
                      </Link>
                    </li>

                    <li>
                      <a
                        href="#"
                        className="block rounded-lg px-4 py-2 text-sm text-left font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                      >
                        Security
                      </a>
                    </li>

                    <li>
                      <Link
                        to="/"
                        className="block rounded-lg px-4 py-2 text-sm text-left font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                      >
                        Logout
                      </Link>
                    </li>
                  </ul>
                </details>
              </li>
            </div>
            <div className="flex items-center gap-2 bg-background p-4 hover:bg-background">
              <Avatar>
                <AvatarFallback>{getAvatarFallBack()}</AvatarFallback>
              </Avatar>

              <div>
                <p className="text-xs text-left">
                  <strong className="block font-medium">
                    {userProfile.fullName}
                  </strong>

                  <span> {userProfile.email}</span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="flex justify-between p-2 gap-1 sm:hidden">
        <div className="flex gap-2">
          <Avatar>
            <AvatarFallback>{getAvatarFallBack()}</AvatarFallback>
          </Avatar>

          <Menus className="w-min" />
        </div>
        <ModeToggle />
      </div>
      <div className="w-full min-h-lvh p-2 ml-2 sm:ml-60">
        <Outlet />
      </div>
    </div>
  );
}

export default MainLayout;
