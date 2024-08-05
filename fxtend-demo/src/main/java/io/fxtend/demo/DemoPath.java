package io.fxtend.demo;

public enum DemoPath
{
    DEMO_PATH_STYLE("demo-style.css");

    private final String path;

    DemoPath(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }
}
