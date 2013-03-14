/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011, 2012, FrostWire(TM). All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.frostwire.android.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author gubatron
 * @author aldenml
 *
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * @param parentDir
     */
    public static File createFolder(File parentDir, String folderName) {
        File f = new File(parentDir, folderName);
        if (!f.exists() || !f.isDirectory()) {
            f.mkdir();
        }
        return f;
    }

    public static void deleteFolderRecursively(File folder) {
        if (folder != null && folder.isDirectory() && folder.canWrite()) {
            //delete your contents and recursively delete sub-folders
            File[] listFiles = folder.listFiles();

            if (listFiles != null) {
                for (File f : listFiles) {
                    if (f.isFile()) {
                        f.delete();
                    } else if (f.isDirectory()) {
                        deleteFolderRecursively(f);
                    }
                }
                folder.delete();
            }
        }
    }

    public static boolean deleteEmptyDirectoryRecursive(File directory) {
        // make sure we only delete canonical children of the parent file we
        // wish to delete. I have a hunch this might be an issue on OSX and
        // Linux under certain circumstances.
        // If anyone can test whether this really happens (possibly related to
        // symlinks), I would much appreciate it.
        String canonicalParent;
        try {
            canonicalParent = directory.getCanonicalPath();
        } catch (IOException ioe) {
            return false;
        }

        if (!directory.isDirectory()) {
            return false;
        }

        boolean canDelete = true;

        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
                if (!files[i].getCanonicalPath().startsWith(canonicalParent))
                    continue;
            } catch (IOException ioe) {
                canDelete = false;
            }

            if (!deleteEmptyDirectoryRecursive(files[i])) {
                canDelete = false;
            }
        }

        return canDelete ? directory.delete() : false;
    }

    public static String getValidFileName(String fileName) {
        String newFileName = fileName.replaceAll("[\\\\/:*?\"<>|\\[\\]]+", "_");
        return newFileName;
    }

    /** Given a folder path it'll return all the files contained within it and it's subfolders
     * as a flat set of Files.
     * 
     * Non-recursive implementation, up to 20% faster in tests than recursive implementation. :)
     * 
     * @author gubatron
     * @param folder
     * @param extensions If you only need certain files filtered by their extensions, use this string array (without the "."). or set to null if you want all files. e.g. ["txt","jpg"] if you only want text files and jpegs.
     * 
     * @return The set of files.
     */
    public static Collection<File> getAllFolderFiles(File folder, String[] extensions) {
        Set<File> results = new HashSet<File>();
        Stack<File> subFolders = new Stack<File>();
        File currentFolder = folder;
        while (currentFolder != null && currentFolder.isDirectory() && currentFolder.canRead()) {
            File[] fs = currentFolder.listFiles();
            for (File f : fs) {
                if (!f.isDirectory()) {
                    if (extensions == null || FilenameUtils.isExtension(f.getName(), extensions)) {
                        results.add(f);
                    }
                } else {
                    subFolders.push(f);
                }
            }
            if (!subFolders.isEmpty()) {
                currentFolder = subFolders.pop();
            } else {
                currentFolder = null;
            }
        }
        return results;
    }
}
