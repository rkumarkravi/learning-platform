import { Theme, useTheme } from "@/components/ThemeProvider";
import {
  Menubar,
  MenubarContent,
  MenubarItem,
  MenubarMenu,
  MenubarRadioGroup,
  MenubarRadioItem,
  MenubarSeparator,
  MenubarTrigger,
} from "@/components/ui/menubar";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

export function Menus() {
  const userProfile = useSelector((state: any) => state.userProfile.value);
  const { theme, setTheme } = useTheme();
  const navigate = useNavigate();
  function setThemeMenu(event: Theme) {
    setTheme(event);
  }

  function navigateToPath(path: string) {
    navigate(path);
  }

  function logout(arg0: string): void {
    localStorage.removeItem("at");
    navigateToPath(arg0);
  }

  function navigateToDashboard(): void {
    if (userProfile && userProfile.role === "TEACHER") navigate("creator");
    else navigate("student");
  }

  return (
    <Menubar className="m-2">
      <MenubarMenu>
        <MenubarTrigger>Profile</MenubarTrigger>
        <MenubarContent>
          <MenubarItem onClick={() => navigateToDashboard()}>
            Dashboard
          </MenubarItem>
          <MenubarItem>Profile Settings</MenubarItem>
          <MenubarItem onClick={() => navigateToPath("viewProfile")}>
            View Profile
          </MenubarItem>
          <MenubarSeparator />
          <MenubarItem onClick={() => logout("/")}>Logout</MenubarItem>
        </MenubarContent>
      </MenubarMenu>
      <MenubarMenu>
        <MenubarTrigger>Course</MenubarTrigger>
        <MenubarContent>
          <MenubarItem>New Course</MenubarItem>
          <MenubarItem>Update Course Material</MenubarItem>
          <MenubarItem>Add Quizzes</MenubarItem>
          <MenubarSeparator />
          <MenubarItem>Student Enrolled</MenubarItem>
          <MenubarItem>Course Analytics</MenubarItem>
        </MenubarContent>
      </MenubarMenu>
      {/* <MenubarMenu>
        <MenubarTrigger>Edit</MenubarTrigger>
        <MenubarContent>
          <MenubarItem>
            Undo <MenubarShortcut>⌘Z</MenubarShortcut>
          </MenubarItem>
          <MenubarItem>
            Redo <MenubarShortcut>⇧⌘Z</MenubarShortcut>
          </MenubarItem>
          <MenubarSeparator />
          <MenubarSub>
            <MenubarSubTrigger>Find</MenubarSubTrigger>
            <MenubarSubContent>
              <MenubarItem>Search the web</MenubarItem>
              <MenubarSeparator />
              <MenubarItem>Find...</MenubarItem>
              <MenubarItem>Find Next</MenubarItem>
              <MenubarItem>Find Previous</MenubarItem>
            </MenubarSubContent>
          </MenubarSub>
          <MenubarSeparator />
          <MenubarItem>Cut</MenubarItem>
          <MenubarItem>Copy</MenubarItem>
          <MenubarItem>Paste</MenubarItem>
        </MenubarContent>
      </MenubarMenu>
      <MenubarMenu>
        <MenubarTrigger>View</MenubarTrigger>
        <MenubarContent>
          <MenubarCheckboxItem>Always Show Bookmarks Bar</MenubarCheckboxItem>
          <MenubarCheckboxItem checked>
            Always Show Full URLs
          </MenubarCheckboxItem>
          <MenubarSeparator />
          <MenubarItem inset>
            Reload <MenubarShortcut>⌘R</MenubarShortcut>
          </MenubarItem>
          <MenubarItem disabled inset>
            Force Reload <MenubarShortcut>⇧⌘R</MenubarShortcut>
          </MenubarItem>
          <MenubarSeparator />
          <MenubarItem inset>Toggle Fullscreen</MenubarItem>
          <MenubarSeparator />
          <MenubarItem inset>Hide Sidebar</MenubarItem>
        </MenubarContent>
      </MenubarMenu> */}
      <MenubarMenu>
        <MenubarTrigger>Theme</MenubarTrigger>
        <MenubarContent>
          <MenubarRadioGroup value={theme} onValueChange={setThemeMenu}>
            <MenubarRadioItem value="light">Light</MenubarRadioItem>
            <MenubarRadioItem value="dark">Dark</MenubarRadioItem>
            <MenubarRadioItem value="system">System</MenubarRadioItem>
          </MenubarRadioGroup>
        </MenubarContent>
      </MenubarMenu>
    </Menubar>
  );
}
