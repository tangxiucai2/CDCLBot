package cdcl.bot.logger;

import cdcl.bot.core.DiscordBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Logger extends ListenerAdapter {

    private TextChannel channel;

    public TextChannel getChannel() {
        return channel;
    }

    public void setChannel(TextChannel channel) {
        this.channel = channel;
    }

    //EVENTS

    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent event) {
        if(channel == null) return;
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getEffectiveName() + '#' + event.getMember().getUser().getDiscriminator(), null, event.getMember().getUser().getAvatarUrl());
        builder.setTitle("**Message edited in #" + event.getChannel().getName() + "**");
        //builder.addField("Before", event.getMessage()., false);

        channel.sendMessage(builder.build()).queue();
        super.onGuildMessageUpdate(event);
    }
}
