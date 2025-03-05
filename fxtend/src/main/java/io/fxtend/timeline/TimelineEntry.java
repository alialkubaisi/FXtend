package io.fxtend.timeline;

import java.util.Date;

public record TimelineEntry(TimelineTextConstants action, String username, String description, Date date)
{
}
